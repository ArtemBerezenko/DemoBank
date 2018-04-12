$(document).ready(function() {
    $('#amountFrom').change(function () {
        var amountFrom = $(this).val();
        console.log("amountFrom " + amountFrom);
        $.ajax({
            type: "GET",
            url: "/getRate",
            data: {
                amountFrom: amountFrom, currencyFrom: $('#currency_from').val(),
                currencyTo: $('#currency_to').val(), amountTo: $('#amountTo').val()
            },
            success: function (data) {
                $('#amountTo').val(data);
                console.log("amountTo: ", $('#amountTo').val());
            }
        });
    });
});
