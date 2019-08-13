const mealAjaxUrl = "ajax/profile/meals/";

$("#dateTime").datetimepicker({
    format: "Y-m-d H:i"
});

$("#startDate, #endDate").datetimepicker({
   timepicker:false,
    format: "Y-m-d"
});

$("#startTime, #endTime").datetimepicker({
   datepicker:false,
    format: "H:i"
});

$.ajaxSetup({
    converters: {
        "text json": function (data) {
            let meal = JSON.parse(data);
            $(meal).each(function () {
                this.dateTime = this.dateTime.replace("T", " ").substring(0, 16);
            });
            return meal;
        }
    }
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (data.excess) {
                    $(row).css("color", "blue");
                } else {
                    $(row).css("color", "green");
                }
            }
        }),
        updateTable: updateFilteredTable
    });
});