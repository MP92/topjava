var ajaxUrl = 'ajax/profile/meals/';
var dataTableApi;

$(function() {
/*    datatableApi = $('#mealsTable').dataTable({
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "dateTime"
            },
            {
                "mData": "description"
            },
            {
                "mData": "calories"
            },
            {
                "sDefaultContent": "",
                "bSortable": false
            },
            {
                "sDefaultContent": "",
                "bSortable": false
            }
        ],
        "aaSorting": [
            [
                0,
                "asc"
            ]
        ]
    });*/

    dataTableApi = $('#mealsTable').DataTable({
        paging: false,
        info: false,
        columns: [
            {"data": "dateTime"},
            {"data": "description"},
            {"data": "calories"},
            {
                "defaultContent": "",
                "orderable": false
            },
            {
                "defaultContent": "",
                "orderable": false
            }
        ],
        order: [[0, "asc"]]
    });

    makeEditable();
});
