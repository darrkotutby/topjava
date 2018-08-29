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
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        draw(data);
    });
}

function setEnabled(id, checked, element) {
    $.ajax({
        type: "POST",
        url: ajaxUrl + id + "/setEnabled",
        data: "enabled=" + checked,
        success: function () {
            if (checked) {
                element.closest("tr").setAttribute("data-enabledUser", "true");
            }
            else {
                element.closest("tr").setAttribute("data-enabledUser", "false");
            }
            successNoty("Enable changed");
        }
    });
}
