let tr, enabled, checkbox;
// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
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
            })
        }
    );
    $(".enabled").change(function () {
        tr = $(this).parents("tr");
        checkbox = $(this);
        enabled = this.checked;
        $.ajax({
            type: "POST",
            url: context.ajaxUrl + "enable",
            data: "id=" + tr.attr("id") + "&enabled=" + enabled
        }).done(function() {
            enabled ? tr.css("color", "black") : tr.css("color", "#aeaeae");
            successNoty("Changed");
        }).fail(function() {
            enabled ? checkbox.prop("checked", false) : checkbox.prop("checked", true);
        });
    });
});

function updateTable() {
    $.get(context.ajaxUrl, function(data) {
        drawTable(data)
    });
}