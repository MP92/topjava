$(function () {
    $('a.locale-option').click(function() {
        var targetUrl = window.location.pathname + '?locale=' + $(this).data('locale');
        window.location.href = targetUrl;
        return false;
    })
});
