let getMethodToAPI = function (service) {
    var jqxhr = $.get(service);
    return jqxhr;
};

let postMethodToAPI = function (service, data) {
    return $.ajax({
        type: 'POST',
        url: service,
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
};

let deleteMethodToAPI = function (service, data) {
    return $.ajax({
        type: 'DELETE',
        url: service,
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
};

let putMethodToAPI = function (service, data) {
    return $.ajax({
        type: 'PUT',
        url: service,
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
};