var intervalLimitWeeks = 8;

var documentCreateBtn = $('#documentCreateBtn');
var createDocForm = $('#createDocForm');

var createDocConfirm = $.confirm({
    icon: 'fa fa-warning',
    title: 'Warning',
    content: 'Client received a voucher less than ' + intervalLimitWeeks + ' weeks ago! Do you want to create a new one?',
    lazyOpen: true,
    closeIcon: true,
    closeIconClass: 'fa fa-close',
    buttons: {
        create: {
            btnClass: 'btn-primary',
            action: function(){
                createDocForm.submit();
            }
        },
        cancel: {
            btnClass: 'btn-secondary'
        }
    }
});

documentCreateBtn.on('click', function (event) {
    var btn = $(this);
    var clientId = btn.data('client-id');
    $.ajax({
        type: 'GET',
        url: '/api/document/check/' + clientId,
        contentType: 'application/json',
        dataType: "json"
    }).then(function (data) {
        if (data.responseType === 'SUCCESS') {
            if(data.result.received === false){
                createDocForm.submit();
            } else {
                intervalLimitWeeks = data.result.weeks;
                createDocConfirm.content = 'Client received a voucher less than ' + intervalLimitWeeks + ' weeks ago! Do you want to create a new one?';
                createDocConfirm.open();
            }
        } else if (data.responseType === 'FORM_ERROR') {
            alert('Server error');
        }
    }).fail(function (data) {
        clientEditModal.find('.modal-body').append("<div class=\"alert alert-danger\">Server error</div>")
    });
});



