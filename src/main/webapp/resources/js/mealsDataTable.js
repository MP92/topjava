var ajaxUrl = 'ajax/profile/meals/';
var dataTableApi;

$(function() {
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
        order: [[0, "desc"]]
    });

    makeEditable();
});
