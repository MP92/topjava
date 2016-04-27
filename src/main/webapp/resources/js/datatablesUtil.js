function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).closest('tr').attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filter-form').submit(function() {
        updateTable();
        return false;
    });

    $('.user-status').change(function() {
        $.ajax({
            url: ajaxUrl + $(this).closest('tr').attr('id'),
            type: 'POST',
            success: function () {
                updateTable();
                successNoty('Updated');
            }
        });
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    var onSuccessFn = function(data) {
        dataTableApi.clear().rows.add(data).draw();
    };

    var form = $('#filter-form');
    if (form.length > 0) {
        $.get(ajaxUrl + 'filter', form.serialize(), onSuccessFn);
    } else {
        $.get(ajaxUrl, onSuccessFn);
    }
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
