$(function() {
    
$('#create-billing-records-form').submit(function (e) {
    e.preventDefault();
    
    let ratefee = {
        rate: $('#rate').val(),
        quantity: $('#rate-based-quantity'),
        client: { id: $('#rate-based-client').val() },
        description: $('#rate-based-description').val()
    };

    let headers = {
        'X-CSRF-TOKEN': $('#rate-based-csrf').val()
    };
    let settings = {
            url: '/api/billing-records/rate-baseds',
            headers: headers,
            data: JSON.stringify(ratefee),
            contentType: 'application/json'
    };
    $.post(settings)
    .done(function (data) {
        $('#fee-table')
            .append(`
              <tr>
                <td></td>
                <td>${ data.description }</td>
                <td>${ data.client.name }</td>
                <td>${ data.amount }</td>
                <td>${ data.rate }</td>
                <td>${ data.quantity }</td>
                <td>${ data.total }</td>
               </tr>
            `);
        $('#rate').val('');
        $('#rate-based-quantity').val('');
        $('#rate-based-client').val('');
        $('#rate-based-description').val('');
    });
});    

});