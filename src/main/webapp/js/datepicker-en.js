// JavaScript Document
/* Arabic Translation for jQuery UI date picker plugin. */
jQuery(function($){
	        $.datepicker.regional['en'] = {
                monthNames: ['Jan','February','March','April','May','June','July','August','September','October','November','December'],
                monthNamesShort: ['Jan','February','March','April','May','June','July','August','September','October','November','December'],
                dayNames: ['Sunday','Monday','Tuseday','Wednesday','Thursday','Friday','Saturday'],
                dayNamesShort: ['Sun','Mon','Tuse','Wed','Thurs','Fri','Sat'],
                dayNamesMin: ['S','M','T','W','Th','F','S'],
                dateFormat: 'dd/mm/yyyy',
                firstDay: 0,
                prevText: '&#x3c;prev', prevStatus: '',
                prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
                nextText: 'next&#x3e;', nextStatus: '',
                nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
                currentText: 'today', currentStatus: '',
                todayText: 'today', todayStatus: '',
                clearText: '-', clearStatus: '',
                closeText: 'close', closeStatus: '',
                yearStatus: '', monthStatus: '',
                weekText: 'week', weekStatus: '',
                dayStatus: 'DD d MM',
                defaultStatus: '',
                isRTL: true
        };
        $.datepicker.setDefaults($.datepicker.regional['en']);
});
