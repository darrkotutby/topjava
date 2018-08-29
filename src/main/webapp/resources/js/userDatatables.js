var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();

    $(document).ready(function () {
        $(".chbck").on('click', function () {
            var element = $(this);
            var tr = element.closest("tr");
            var userId = $(this).parents("tr").attr("id");
            var checked = $(this).is(':checked');

            $.ajax({
                type: "POST",
                url: ajaxUrl + userId + "/setEnabled",
                data: "enabled=" + checked,
                success: function () {
                    if (checked) {
                        tr.css('color', 'black');
                    }
                    else {
                        tr.css('color', 'lightgray');
                    }
                    successNoty("Enable changed");
                }
            });
        });
    });
});

