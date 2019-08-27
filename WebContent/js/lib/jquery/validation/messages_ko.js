/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: KO
 */
jQuery.extend(jQuery.validator.messages, {
	required	: $.validator.format("{1}을(를) 입력하십시오."),
	remote		: "Please fix this field.",
	email		: $.validator.format("{1}에 유효한 email 주소를 입력하십시오."),
	url			: $.validator.format("{1}에 유효한 URL을 입력하십시오."),
	date		: $.validator.format("{1}에 유효한 날자를 입력하십시오."),
	dateISO		: $.validator.format("{1}에 유효한 날자(ISO)를 입력하십시오."),
	number		: $.validator.format("{1}에 유효한 수자를 입력하십시오."),
	digits		: $.validator.format("{1}에 수자만 입력하십시오."),
	creditcard	: "유효한 credit card수자를 입력하십시오.",
	equalTo		: $.validator.format("{1}와(과) 같은 값을 다시 한번 입력하십시오."),
	accept		: "유효한 확장자가 아닙니다.",
	maxlength	: $.validator.format("{1}은(는) 최대 {0}글자까지 입력가능합니다."),
	minlength	: $.validator.format("{1}은(는) 최소 {0}글자까지 입력가능합니다."),
	rangelength	: $.validator.format("{2}은(는) {0}에서 {1}글자까지 입력가능합니다."),
	range		: $.validator.format("{2}은(는) {0}에서 {1}까지 입력가능합니다."),
	max			: $.validator.format("{1}은(는) {0}와(과) 같거나  작은수를 입력하십시오."),
	min			: $.validator.format("{1}은(는) {0}와(과) 같거나 큰수를 입력하십시오.")
});