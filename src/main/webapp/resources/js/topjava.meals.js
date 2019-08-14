const mealAjaxUrl = "ajax/profile/meals/";

$("#dateTime").datetimepicker({
    format: "Y-m-d H:i"
});

$(function(){
    $("#startDate").datetimepicker({
        format:"Y-m-d",
        onShow:function(ct){
            let endDate = $("#endDate").val();
            this.setOptions({
                maxDate: endDate ? endDate : false
            })
        },
        timepicker:false
    });
    $("#endDate").datetimepicker({
        format:"Y-m-d",
        onShow:function(ct){
            let startDate = $("#startDate").val();
            this.setOptions({
                minDate: startDate ? startDate : false
            })
        },
        timepicker:false
    });
});

$(function(){
    $("#startTime").datetimepicker({
        format:"H:i",
        onShow:function(ct){
            let endTime = $("#endTime").val();
            this.setOptions({
                maxTime: ($("#startDate").val() === $("#endDate").val()) && endTime ? endTime : false
            })
        },
        datepicker:false
    });
    $("#endTime").datetimepicker({
        format:"H:i",
        onShow:function(ct){
            let startTime = $("#startTime").val();
            this.setOptions({
                minTime: ($("#startDate").val() === $("#endDate").val()) && startTime ? startTime : false
            })
        },
        datepicker:false
    });
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
                $(row).css("color", data.excess ? "blue" : "green");
            }
        }),
        updateTable: updateFilteredTable
    });
});