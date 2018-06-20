$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
    var actions = $(".membersTable td:last-child").html();
    // Append table with add row form on add new button click
    $(".add-new").click(function () {
        // $(this).attr("disabled", "disabled");
        var index = $(".membersTable tbody tr:last-child").index() + 1;
        var row = '<tr>' +
            '<td><input type="text" class="form-control" name="members[' + index + '].firstName" id="members[' + index + '].firstName" placeholder="First name"></td>' +
            '<td><input type="text" class="form-control" name="members[' + index + '].lastName" id="members[' + index + '].lastName" placeholder="Last name"></td>' +
            '<td><input type="text" class="form-control birth-date-picker" name="members[' + index + '].birthDate" id="members[' + index + '].birthDate" placeholder="Birth date"></td>' +
            '<td><a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">delete</i></a></td>' +
            '</tr>';
        $(".membersTable").append(row);

        $('.birth-date-picker').datepicker({
            format: "mm/dd/yyyy",
            autoclose: true
        });
        // $("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
        // $('[data-toggle="tooltip"]').tooltip();
    });

    var inputs = $(".membersTable").find('input[type="text"]');
    inputs.each(function () {
        if (!$(this).val()) {
            $(this).addClass("error");
            empty = true;
        } else {
            $(this).removeClass("error");
        }
    });
    // Add row on add button click
    // $(document).on("click", ".add", function(){
    //     var empty = false;
    //     var input = $(this).parents("tr").find('input[type="text"]');
    //     input.each(function(){
    //         if(!$(this).val()){
    //             $(this).addClass("error");
    //             empty = true;
    //         } else{
    //             $(this).removeClass("error");
    //         }
    //     });
    //     $(this).parents("tr").find(".error").first().focus();
    //     if(!empty){
    //         input.each(function(){
    //             $(this).parent("td").html($(this).val());
    //         });
    //         $(this).parents("tr").find(".add, .edit").toggle();
    //         $(".add-new").removeAttr("disabled");
    //     }
    // });
    // Edit row on edit button click
    // $(document).on("click", ".edit", function(){
    //     $(this).parents("tr").find("td:not(:last-child)").each(function(){
    //         $(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
    //     });
    //     $(this).parents("tr").find(".add, .edit").toggle();
    //     $(".add-new").attr("disabled", "disabled");
    // });
    // Delete row on delete button click
    $(document).on("click", ".delete", function () {
        $(this).parents("tr").remove();
        // $(".add-new").removeAttr("disabled");
    });
});