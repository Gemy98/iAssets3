// JavaScript Document
/* Arabic Translation for jQuery UI date picker plugin. */
jQuery(function($){
	        $.datepicker.regional['ar'] = {
                monthNames: ['يناير','فبراير','مارس','أبريل','مايو','يونيه','يوليو','أغسطس','سبتمبر','أكتوبر','نوفمبر','ديسمبر'],
                monthNamesShort: ['يناير','فبراير','مارس','أبريل','مايو','يونيه','يوليو','أغسطس','سبتمبر','أكتوبر','نوفمبر','ديسمبر'],
                dayNames: ['الأحد','الاثنين','الثلاثاء','الأربعاء','الخميس','الجمعة','السبت'],
                dayNamesShort: ['أحد','اثنين','ثلاثاء','أربعاء','خميس','جمعة','سبت'],
                dayNamesMin: ['أحد','اثنين','ثلاثاء','أربعاء','خميس','جمعة','سبت'],
                dateFormat: 'dd/mm/yyyy',
                firstDay: 0,
                prevText: '&#x3c;السابق', prevStatus: '',
                prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
                nextText: 'التالي&#x3e;', nextStatus: '',
                nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
                currentText: 'اليوم', currentStatus: '',
                todayText: 'اليوم', todayStatus: '',
                clearText: '-', clearStatus: '',
                closeText: 'إغلاق', closeStatus: '',
                yearStatus: '', monthStatus: '',
                weekText: 'أسبوع', weekStatus: '',
                dayStatus: 'DD d MM',
                defaultStatus: '',
                isRTL: true
        };
        $.datepicker.setDefaults($.datepicker.regional['ar']);
});
