var userEditModal = $('#userEditModal');
var userEditModalForm = $('#userEditModalForm');
var userEditModalSubmit = $('#userEditModalSubmit');

$('#createUserBtn').on('click', function (event) {
    userEditModalForm.attr('action', '/api/user');
    userEditModalSubmit.text('Save');
    clearUserEditModel();
});

function clearUserEditModel() {
    clearUserEditModelErrors();
    userEditModalForm.removeClass('prefillForm-prefiller prefillForm-prefilled');
    userEditModalForm[0].reset();
}

function clearUserEditModelErrors() {
    userEditModalForm.find(".errorDesc").remove();
    userEditModal.find('.alert').remove();
    userEditModalForm.find("input").removeClass('fieldError');
}

$('.user-list-td').on('click', function (event) {
    clearUserEditModel();
    var td = $(this);
    var tr = td.parent();
    var id = tr.data('id');
    var url = '/api/user/' + id;
    $.ajax({
        url: url

    }).then(function (data) {
        userEditModalForm.populate(data.result);
        userEditModalForm.attr('action', url);
        userEditModalSubmit.text('Update');
        userEditModal.modal('show');
    });
});

userEditModalSubmit.on('click', function (event) {
    clearUserEditModelErrors();
    var formData = JSON.stringify(userEditModalForm.serializeJSON());
    $.ajax({
        type: 'POST',
        url: userEditModalForm.attr('action'),
        contentType: 'application/json',
        dataType: "json",
        data: formData
    }).then(function (data) {
        if (data.responseType === 'SUCCESS') {
            userEditModal.find('.modal-body').append("<div class=\"alert alert-success\">Success</div>");
            var userId = data.result;
            userEditModalForm.attr('action', '/api/user/' + userId);
            userEditModalSubmit.text('Update');
        } else if (data.responseType === 'FORM_ERROR') {
            data.result.forEach(function (element) {
                var input = userEditModalForm.find('#userEditModal-' + element.field);
                input.addClass('fieldError');
                input.after("<span class=\"errorDesc\"> " + element.defaultMessage + "</span>");
            });
        }
    }).fail(function (data) {
        userEditModal.find('.modal-body').append("<div class=\"alert alert-danger\">Server error</div>")
    });
});