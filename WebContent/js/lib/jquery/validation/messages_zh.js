/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH
 */
jQuery.extend(jQuery.validator.messages, {
	required	: $.validator.format("请输入{1}。"),
	remote		: "请修改本字段。",
	email		: $.validator.format("请输入正确电子邮件。"),
	url			: $.validator.format("请输入正确网址。"),
	date		: $.validator.format("请输入正确日期。"),
	dateISO		: $.validator.format("请输入正确日期(ISO)。"),
	number		: $.validator.format("请输入正确数字。"),
	digits		: $.validator.format("只能输入数字。"),
	creditcard	: "请输入正确信用卡帐号信息。",
	equalTo		: $.validator.format("请再次确认密码"),
	accept		: "文件类型错误。",
	maxlength	: $.validator.format("{1}可输入最多 {0}位字数。"),
	minlength	: $.validator.format("{1}可输入最小 {0}位字数。"),
	rangelength	: $.validator.format("{2}可输入 {0}位到 {1}位字数。"),
	range		: $.validator.format("{2}可输入 {0}到 {1}。"),
	max			: $.validator.format("{1}输入{0}以下的数值。"),
	min			: $.validator.format("{1}输入 {0}以下的数值。")
});