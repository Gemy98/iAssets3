/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: AR (Arabic; العربية)
 */
(function ($) {
	$.extend($.validator.messages, {
		required: "هذا الحقل إلزامي",
		remote: "يرجى تصحيح هذا الحقل للمتابعة",
		email: "رجاء إدخال عنوان بريد إلكتروني صحيح",
		url: "رجاء إدخال عنوان موقع إلكتروني صحيح",
		date: "رجاء إدخال تاريخ صحيح",
		dateISO: "رجاء إدخال تاريخ صحيح (ISO)",
		number: "من فضلك أدخل رقم بصورة صحيحة",
		digits: "مسموح بأرقام صحيحة فقط",
		integer: "مسموح بأرقام صحيحة فقط",
		positiveNumber: "مسموح بأرقام صحيحة موجبة فقط",
		creditcard: "رجاء إدخال رقم بطاقة ائتمان صحيح",
		equalTo: "رجاء إدخال نفس القيمة",
		accept: "رجاء إدخال ملف بامتداد موافق عليه",
		maxlength: $.validator.format("الحد الأقصى لعدد الحروف هو {0}"),
		minlength: $.validator.format("الحد الأدنى لعدد الحروف هو {0}"),
		rangelength: $.validator.format("عدد الحروف يجب أن يكون بين {0} و {1}"),
		range: $.validator.format("رجاء إدخال عدد قيمته بين {0} و {1}"),
		max: $.validator.format("رجاء إدخال عدد أقل من أو يساوي {0}"),
		min: $.validator.format("رجاء إدخال عدد أكبر من أو يساوي {0}"),
		filesize: $.validator.format("أحد الملفات المرفقة تعدى حجمه الحد الأقصى المسموح وهو  {0} ميجا بايت"),
		selectrequired: "هذا الحقل إلزامي",
		maxattach: $.validator.format("الحد الأقصى المسموح به لعدد المرفقات هو {0} مرفقات"),
		filerequired:"هذا الحقل إلزامي"
	});
}(jQuery));