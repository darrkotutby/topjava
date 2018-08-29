var ajaxUrl = "ajax/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

function updateTableFiltered() {
    var form = $("#mealsFilterForm");
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function (data) {
            s(data);
        }} );
}

function f() {
    updateTableFiltered(document.getElementById("startDate").value, document.getElementById("endDate").value,
        document.getElementById("startTime").value, document.getElementById("endTime").value);
}