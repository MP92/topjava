var ajaxUrl = 'ajax/admin/users/';
var dataTableApi;

// $(document).ready(function () {
$(function () {
/*    dataTableApi = $('#datatable').dataTable({
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "name"
            },
            {
                "mData": "email"
            },
            {
                "mData": "roles"
            },
            {
                "mData": "enabled"
            },
            {
                "mData": "registered"
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

    dataTableApi = $('#datatable').DataTable({
        paging: false,
        info: false,
        columns: [
            {"data": "name"},
            {"data": "email"},
            {"data": "roles"},
            {"data": "enabled"},
            {"data": "registered"},
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