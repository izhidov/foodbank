function setActionToAgeInputs() {
    var ageInputs = $(".membersTable").find('input[type="text"].member-age');
    ageInputs.each(function () {
        var ageInput = $(this);
        ageInput.on('blur', function () {
            if (ageInput.val()) {
                var dob = getDob(ageInput.val());
                var dobInput = ageInput.parent().prev().find('input[type="text"]');
                dobInput.val(dob);
                dobInput.removeClass("error");
            }
        })
    });
}

function openMembersModal(anchor){
    var id = anchor.data('id');
    var url = '/api/member/' + id;
    $.ajax({
        url: url
    }).then(function (data) {
        $('#membersModalHolder').html(data);
        $("#membersModal").on("shown.bs.modal", function () {
            $('#membersModalSave').on('click', function () {
                saveMembers();

            });
            $(".add-new").click(function () {
                var row = '<tr>' +
                    '<td><input type="text" class="form-control member-fn" name="firstName"></td>' +
                    '<td><input type="text" class="form-control member-ln" name="lastName"></td>' +
                    '<td><input type="checkbox" class="form-control member-active" name="active"></td>' +
                    '<td><input type="text" class="form-control birth-date-picker member-dob" name="birthDate"></td>' +
                    '<td><input type="text" class="form-control member-age"></td>' +
                    '<td><a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">delete</i></a></td>' +
                    '</tr>';
                $(".membersTable").append(row);

                $('.birth-date-picker').datepicker({
                    format: "mm/dd/yyyy",
                    autoclose: true
                });
                setActionToAgeInputs();
            });

            setActionToAgeInputs();

            $('.birth-date-picker').datepicker({
                format: "mm/dd/yyyy",
                autoclose: true
            });
        }).modal('show');
    });
}

$("a.manage-members").on('click', function () {
   openMembersModal($(this));
});

function getDob(age){
    var today = new Date();
    var year = today.getFullYear() - age;
    return '01/01/' + year;
}

function saveMembers() {
    var selected = new Array();
    $('.membersTable').find('tr').each(function () {
        var id = $(this).find('input[type="hidden"]').val();
        var fn = $(this).find('input[type="text"].member-fn').val();
        var ln = $(this).find('input[type="text"].member-ln').val();
        var dob = $(this).find('input[type="text"].member-dob').val();
        var active = $(this).find('input[type="checkbox"].member-active').is(':checked');
        if(dob) {
            selected.push({
                'id': id,
                'firstName': fn,
                'lastName': ln,
                'birthDate': dob,
                'active': active
            });
        }
    });

    var clientId = $('.membersTable').data('id');
    var stringify = JSON.stringify(selected);
    $.ajax
    ({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/api/member/" + clientId,
        data: stringify,
        dataType: "json",
        success: function (data) {
            $("#membersModal").find('.modal-body').append("<div class=\"alert alert-success\">Success</div>");
        },
        error: function (x, e) {
            $("#membersModal").find('.modal-body').append("<div class=\"alert alert-danger\">Server error</div>")
        }
    });
}

$(document).ready(function () {
    $(".add-new").click(function () {
        var index = $(".membersTable tbody tr:last-child").index() + 1;
        var row = '<tr>' +
            '<td><input type="text" class="form-control" name="members[' + index + '].firstName" id="members[' + index + '].firstName" placeholder="First name"></td>' +
            '<td><input type="text" class="form-control" name="members[' + index + '].lastName" id="members[' + index + '].lastName" placeholder="Last name"></td>' +
            '<td><input type="text" class="form-control birth-date-picker" name="members[' + index + '].birthDate" id="members[' + index + '].birthDate" placeholder="Birth date"></td>' +
            '<td><input type="text" class="form-control member-age" placeholder="Age"></td>' +
            '<td><a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">delete</i></a></td>' +
            '</tr>';
        $(".membersTable").append(row);

        $('.birth-date-picker').datepicker({
            format: "mm/dd/yyyy",
            autoclose: true
        });

        setActionToAgeInputs();
    });

    setActionToAgeInputs();

    // var inputs = $(".membersTable").find('input[type="text"]');
    // inputs.each(function () {
    //     if (!$(this).val()) {
    //         $(this).addClass("error");
    //         empty = true;
    //     } else {
    //         $(this).removeClass("error");
    //     }
    // });

    // Delete row on delete button click
    $(document).on("click", ".delete", function () {
        $(this).parents("tr").remove();
        // $(".add-new").removeAttr("disabled");
    });
});
