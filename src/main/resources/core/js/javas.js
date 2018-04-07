$(document).ready(function() {
    $.ajax({
        type: "Post",
        url: "http://localhost:8080/user/home"
    }).then(function(data, status, jqxhr) {
        $('.amountFrom').append(data.id)
        $('.currency_from').append(data.id);
        $('.currency_to').append(data.id);
        console.log(jqxhr);
    });
});
