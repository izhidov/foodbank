var clientEditModelData = {};

var clientEditModal = $('#clientEditModal');
var clientEditModalForm = $('#clientEditModalForm');
var clientEditModalSubmit = $('#clientEditModalSubmit');
var clientEditModalCreateDocBtn = $('#clientEditModalCreateDocBtn');

$('#createClientBtn').on('click', function (event) {
    clientEditModalForm.attr('action', '/api/client');
    clientEditModalSubmit.text('Save');
    clearClientEditModel();
    hideClientEditModalCreateDocBtn();
});

function hideClientEditModalCreateDocBtn() {
    if(!clientEditModalCreateDocBtn.hasClass('d-none')) {
        clientEditModalCreateDocBtn.addClass('d-none');
    }
    clientEditModalCreateDocBtn.attr('href', '#')
}

function showClientEditModalCreateDocBtn(clientId) {
    if(clientEditModalCreateDocBtn.hasClass('d-none')) {
        clientEditModalCreateDocBtn.removeClass('d-none');
    }
    clientEditModalCreateDocBtn.attr('href', '/api/document/precreate/fromClient/' + clientId)
}

function clearClientEditModel() {
    clearClientEditModelErrors();
    clientEditModalForm.removeClass('prefillForm-prefiller prefillForm-prefilled');
    clientEditModalForm[0].reset();
}

function clearClientEditModelErrors() {
    clientEditModalForm.find(".errorDesc").remove();
    clientEditModal.find('.alert').remove();
    clientEditModalForm.find("input").removeClass('fieldError');
}

$('.client-list-td').on('click', function (event) {
    clearClientEditModel();
    var td = $(this);
    var tr = td.parent();
    var id = tr.data('id');
    var url = '/api/client/' + id;
    showClientEditModalCreateDocBtn(id);
    $.ajax({
        url: url

    }).then(function (data) {
        clientEditModalForm.populate(data.result);
        clientEditModalForm.attr('action', url);
        clientEditModalSubmit.text('Update');
        clientEditModal.modal('show');
    });
});

clientEditModalSubmit.on('click', function (event) {
    clearClientEditModelErrors();
    var formData = JSON.stringify(clientEditModalForm.serializeJSON());
    $.ajax({
        type: 'POST',
        url: clientEditModalForm.attr('action'),
        contentType: 'application/json',
        dataType: "json",
        data: formData
    }).then(function (data) {
        if (data.responseType === 'SUCCESS') {
            clientEditModal.find('.modal-body').append("<div class=\"alert alert-success\">Success</div>");
            var clientId = data.result;
            clientEditModalForm.attr('action', '/api/client/' + clientId);
            clientEditModalSubmit.text('Update');
            showClientEditModalCreateDocBtn(clientId);
        } else if (data.responseType === 'FORM_ERROR') {
            data.result.forEach(function (element) {
                var input = clientEditModalForm.find('#clientEditModal-' + element.field);
                input.addClass('fieldError');
                input.after("<span class=\"errorDesc\"> " + element.defaultMessage + "</span>");
            });
        }
    }).fail(function (data) {
        clientEditModal.find('.modal-body').append("<div class=\"alert alert-danger\">Server error</div>")
    });
});