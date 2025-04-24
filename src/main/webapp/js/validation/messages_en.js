/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: EN (ENGLISH; الإنجليزية)
 */
(function ($) {
	$.extend($.validator.messages, {
		required: "Required Field",
		remote: "Invalid Value",
		email: "Invalid Email Address",
		url: "Invalid URL",
		date: "Please Enter a Valid Date",
		dateISO: "Please Enter a Valid Date (ISO)",
		number: "Invalid Number",
		digits: "Only Integers are Allowed",
		integer: "Only Integers are Allowed",
		positiveNumber: "Only Positive Numbers are Allowed",
		creditcard: "Invalid Credit Card Number",
		equalTo: "Value does not Match",
		accept: "File Extension not Supported",
		maxlength: $.validator.format("Max Allowed Length is {0} Characters"),
		minlength: $.validator.format("Min Allowed Length is {0} Characters"),
		rangelength: $.validator.format("Length Must be between {0} & {1} Characters"),
		range: $.validator.format("Please Enter Number between {0} & {1}"),
		max: $.validator.format("Please Enter Number Less than or Equal to {0}"),
		min: $.validator.format("Please Enter Number Greater than or Equal to {0}"),
		filesize: $.validator.format("One or More Files Exceed Allowed Size of {0} MB"),
		selectrequired: "Required Field",
		maxattach: $.validator.format("Max Allowed Number of Attachments is {0}"),
		filerequired:"Required Field"
	});
}(jQuery));