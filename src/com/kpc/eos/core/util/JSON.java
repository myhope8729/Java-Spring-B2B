package com.kpc.eos.core.util;

import java.util.*;
import java.io.*;
import java.net.*;

public class JSON
{
  public static StringBuffer json_trim(StringBuffer str, char a, char b)
  {
    int begin = 0, end = str.length();
    boolean a_found = false;
    for (int i = 0, n = end; i < n; i++) {
        char c = str.charAt(i);
        if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
            begin = i+1;
            continue;
        }
        if (! a_found && c == a) {
            if (c == a) { a_found = true; }
            begin = i+1;
            continue;
        }
        break;
    }

    boolean b_found = false;
    for (int i = end-1; i >= begin && i >= 0; i--) {
        char c = str.charAt(i);
        if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
            end = i;
            continue;
        }
        if (! b_found && c == b) {
            end = i;
            if (c == b) { b_found = true; }
            continue;
        }
        break;
    }


    return str.delete(end, str.length()).delete(0, begin);
  }

  public static char json_type(StringBuffer str)
  {
    int f = 0;

    for (int i = 0, n = str.length(); i < n; i++) { 
        char c = str.charAt(i);
        if (c == ' '||c == '\n'||c == '\t'||c == '\r') {
            f = i+1; continue;
        }
        break;
    }

    if (f >= str.length()) { return 'F'; }

    if (str.charAt(f) == '{') { return 'H'; }
    if (str.charAt(f) == '[') { return 'A'; }

    if (str.charAt(f) == '"') {
        int e = -1;
        for (int i = f+1, n = str.length(); i < n; i++) {
            if (str.charAt(i) == '"') {
                if (str.charAt(i-1) == '\\') {
                    continue;
                } else {
                    e = i+1;
                    break;
                }
            }
        }
        if (e > f+1) {
            for (int i = e, n = str.length(); i < n; i++) {
                char c = str.charAt(i);
                if (c == ' '||c == '\n'||c == '\t'||c == '\r') {
                    f = i+1; continue;
                }
                if (c == ',' || c == ':') { return 'C'; }
            }
        }
    } else {
        for (int i = f+1, n = str.length(); i < n; i++) {
            if (str.charAt(i) == ',' || str.charAt(i) == ':') {
                return 'C';
            }
        }
    }
    return 'S';
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
public static StringBuffer[] json_split(StringBuffer str, char delimiter)
  {
    List positions = new ArrayList();
    positions.add(new Integer(-1));
    int depth = 0;
    boolean quote_open = false;
    for (int i = 0, n = str.length(); i < n; i++) {
        char c = str.charAt(i);
        if (! quote_open && c == '"') { quote_open = true; depth += 1; continue; }
        if (quote_open && c == '"') {
            if (str.charAt(i-1) != '\\') {
                quote_open = false; depth -= 1; continue;
            }
        }
        if (! quote_open && c == '{') { depth += 1; continue; }
        if (! quote_open && c == '}') { depth -= 1; continue; }
        if (! quote_open && c == '[') { depth += 1; continue; }
        if (! quote_open && c == ']') { depth -= 1; continue; }
        if (depth == 0 && c == delimiter) {
            positions.add(new Integer(i));
        }
    }
    positions.add(new Integer(str.length()));

    StringBuffer[] items = new StringBuffer[positions.size()-1];

    for (int i = 0, n = positions.size(); i < n-1; i++) {
        int x = ((Integer)positions.get(i)).intValue();
        int y = ((Integer)positions.get(i+1)).intValue();
        items[i] = json_trim(new StringBuffer(str.substring(x+1,y)), ' ', ' ');
    }

    return items;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
public static Object json_parse(StringBuffer str)
  {
    if (json_type(str) == 'H') {
        Map m = new HashMap();
        str = json_trim(str, '{', '}');
        StringBuffer[] items = json_split(str, ',');
        for (int i = 0, n = items.length; i < n; i++) {
            StringBuffer[] kv = json_split(items[i], ':');
            if (kv.length == 1) {
              // error
//              System.out.print("ERROR - str:[" + items[i].toString() + "]");
            } else {
//              System.out.print("kv[1]: [" + kv[1].toString() + "]\n");
//              System.out.print("json_type: " + json_type(kv[1]) + "\n");
              if (json_type(kv[1]) != 'S') {
                Object val = json_parse(kv[1]);
                m.put(kv[0].toString(), val);
              } else {
                m.put(json_trim(kv[0], '"', '"').toString(), json_trim(kv[1], '"', '"').toString());
              }
           }
        }
        return m;
    } else if (json_type(str) == 'A') {
        List m = new ArrayList();
        str = json_trim(str, '[', ']');
        StringBuffer[] vals = json_split(str, ',');
        for (int i = 0, n = vals.length; i < n; i++) {
            if (json_type(vals[i]) != 'S') {
                Object val = json_parse(vals[i]);
                m.add(val);
            } else {
                m.add(json_trim(vals[i], '"', '"').toString());
            }
        }
        return m;
    } else {
        return json_trim(str, ' ', ' ').toString();
    }
  }

  public static StringBuffer json_print(Object m)
  {
    StringBuffer buf = new StringBuffer();
    if (m instanceof Map) {
      buf.append("{");
      Iterator it = ((Map)m).entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        String key = (String) pairs.getKey();
        Object v = (Object)pairs.getValue();

        buf.append("\"" + (String) key + "\":");
        if (v instanceof Map || v instanceof List) {
            buf.append(json_print(v).toString());
        } else {
            buf.append("\"");
            buf.append((String) v);
            buf.append("\"");
        }
        if (it.hasNext()) buf.append(",");
      }
      buf.append("}");
    } else if (m instanceof List) {
      buf.append("[");
      for (int i = 0, n = ((List)m).size(); i < n; i++) {
        Object v = ((List)m).get(i);
        if (v instanceof Map || v instanceof List) {
            buf.append(json_print((Map) v).toString());
            
        } else {
            buf.append("\"");
            buf.append((String) v);
            buf.append("\"");
        }
        if (i < n-1) buf.append(",");
      }
      buf.append("]");
    }
    // buf.append("\n");
    return buf;
  }

  public static Object json_post_call(String host, int port, String url_str, String post_args, String cookies)
  {
    StringBuffer buf = new StringBuffer();

    BufferedReader br = null;
    BufferedWriter bw = null;
    try {
      URLConnection conn = (new URL(url_str)).openConnection();
      conn.setConnectTimeout(3000);
      conn.setReadTimeout(3000);

      if (cookies != null && ! cookies.equals("")) {
          conn.setRequestProperty("Cookie", cookies);
      }

      if (post_args != null && ! post_args.equals("")) {
          conn.setDoOutput(true);
          bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
          bw.write(post_args);
          bw.flush();
          bw.close();
      }

      br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line = "";
      while ((line = br.readLine()) != null) {
        buf.append(line);
      }
    } catch (Exception e) {
      System.err.println("ERR-json_post_call():" + e.toString());
      return null;
    } finally {
      try { br.close(); } catch (Exception ee) { } 
      try { bw.close(); } catch (Exception ee) { } 
    }

    if (buf.length() == 0) { return null; }

    return json_parse(buf);
  }

  void test_json_type()
  {
    // assert(json_type(" ") == 'F');
    // assert(json_type(" \"str") == 'F');
    assert(json_type(new StringBuffer(" \"str\\\" test\\\"")) == 'S');
    assert(json_type(new StringBuffer("str\\\" test\\\"")) == 'S');
    assert(json_type(new StringBuffer(" { x }  \n")) == 'H');
    // assert(json_type("    [ x }  \n") == 'F');
    assert(json_type(new StringBuffer("    [ { x ] ]  \n")) == 'A');
    assert(json_type(new StringBuffer(" \"string, string \"  ")) == 'S');
    assert(json_type(new StringBuffer(" string  ")) == 'S');
    assert(json_type(new StringBuffer(" null  ")) == 'S');
    assert(json_type(new StringBuffer(" null, X  ")) == 'C');
  }

  void test_json_trim()
  {
    StringBuffer str = new StringBuffer("\r {\r abc def, dex    } }");
    // System.out.println("[" + json_trim(str, '{', '}').toString() + "]");
    assert(json_trim(str, '{', '}').toString().equals("abc def, dex    }"));
  }

  void test_json_parse()
  {
    System.out.print("test_json_parse() ---------- begin.\n");
    Object m = json_parse(
       new StringBuffer("{ ok: true, mk:  [ \"session-id\"  , b, { cde    : abc}, fx]    , msg: \"Login \\\"Success\", m: \"browser-cookie\" }"));
    System.out.print(json_print(m).toString());
    System.out.print("\ntest_json_parse() ---------------- end.\n");
  }

  void test_json_post_call()
  {
    Object m = json_post_call("127.0.0.1", 80, "http://mail.mobigen.com/m/api-0.1/login.check_passwd.jsp", "userid=login-true", null);
    System.out.print(json_print(m).toString());
  }

  public static void main(String[] args) {
  }
}
