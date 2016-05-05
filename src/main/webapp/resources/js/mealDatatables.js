var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function(date, type, row) {
                    if (type == 'display') {
                        var localISOTime = (new Date(date + 'Z')).toISOString().slice(0,-1);

                        var formattedDate = localISOTime.substring(0, 10) + ' ' + localISOTime.substring(11, 16);
                        return '<span>' + formattedDate + '</span>';
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
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
            if (data.exceed) {
                $(row).css("color", "red");
            }
        },
        "initComplete": makeEditable
    });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });
});