var ajaxUrl = 'ajax/admin/users/';
var dataTableApi;

// $(document).ready(function () {
$(function () {
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