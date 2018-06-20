var organizationEditModal = $('#organizationEditModal');
var organizationEditModalForm = $('#organizationEditModalForm');
var organizationEditModalSubmit = $('#organizationEditModalSubmit');

$('#createOrganizationBtn').on('click', function (event) {
    organizationEditModalForm.attr('action', '/api/organization');
    organizationEditModalSubmit.text('Save');
    clearOrganizationEditModel();
});

function clearOrganizationEditModel() {
    clearOrganizationEditModelErrors();
    organizationEditModalForm.removeClass('prefillForm-prefiller prefillForm-prefilled');
    organizationEditModalForm[0].reset();
}

function clearOrganizationEditModelErrors() {
    organizationEditModalForm.find(".errorDesc").remove();
    organizationEditModal.find('.alert').remove();
    organizationEditModalForm.find("input").removeClass('fieldError');
}

$('.organization-list-td').on('click', function (event) {
    clearOrganizationEditModel();
    var td = $(this);
    var tr = td.parent();
    var id = tr.data('id');
    var url = '/api/organization/' + id;
    $.ajax({
        url: url

    }).then(function (data) {
        organizationEditModalForm.populate(data.result);
        organizationEditModalForm.attr('action', url);
        organizationEditModalSubmit.text('Update');
        organizationEditModal.modal('show');
    });
});

organizationEditModalSubmit.on('click', function (event) {
    clearOrganizationEditModelErrors();
    var formData = JSON.stringify(organizationEditModalForm.serializeJSON());
    $.ajax({
        type: 'POST',
        url: organizationEditModalForm.attr('action'),
        contentType: 'application/json',
        dataType: "json",
        data: formData
    }).then(function (data) {
        if (data.responseType === 'SUCCESS') {
            organizationEditModal.find('.modal-body').append("<div class=\"alert alert-success\">Success</div>");
            var organizationId = data.result;
            organizationEditModalForm.attr('action', '/api/organization/' + organizationId);
            organizationEditModalSubmit.text('Update');
        } else if (data.responseType === 'FORM_ERROR') {
            data.result.forEach(function (element) {
                var input = organizationEditModalForm.find('#organizationEditModal-' + element.field);
                input.addClass('fieldError');
                input.after("<span class=\"errorDesc\"> " + element.defaultMessage + "</span>");
            });
        }
    }).fail(function (data) {
        organizationEditModal.find('.modal-body').append("<div class=\"alert alert-danger\">Server error</div>")
    });
});