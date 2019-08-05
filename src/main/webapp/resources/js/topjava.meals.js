// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
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
            })
        }
    );
});

function updateTable() {
    $.get($("#startDate").val() == ""
    && $("#endDate").val() == ""
    && $("#startTime").val() == ""
    && $("#endTime").val() == ""
        ? context.ajaxUrl
        : context.ajaxUrl + 'filter?' + $("#filter").serialize(), function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $("#startDate").val("");
    $("#endDate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
    $.get(context.ajaxUrl, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}