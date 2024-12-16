$(document).ready(function () {
    const apiUrl = "/api/v1/links";

    // Обработка формы добавления ссылки
    $('#linkForm').on('submit', function (event) {
        event.preventDefault();

        const expirationDateInput = $('#expirationDate').val(); // Получаем значение из input
        const expirationDate = new Date(expirationDateInput);

        // Получаем значения формы
        const linkDto = {
            name: $('#name').val(),
            shortUrl: null,
            originalUrl: $('#link').val(),
            expirationDate: expirationDate,
            creationDate: null,
            numberOfClicks: 0,
            limitOfClicks: $('#limitOfClicks').val()
        };

        // Отправляем POST-запрос на сервер для добавления ссылки
        $.ajax({
            url: `${apiUrl}/add`,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(linkDto),
            success: function () {
                // После успешного добавления перезагружаем данные
                loadLinks();
                $('#linkForm')[0].reset(); // Очищаем форму
            },
            error: function (xhr) {
                alert(`Error adding link: ${xhr.responseText}`);
            }
        });
    });

    // Загрузка ссылок при загрузке страницы
    function loadLinks() {
        $.ajax({
            url: `${apiUrl}/all`, // URL для получения всех ссылок
            method: "GET",
            success: function (links) {
                const $linksTable = $('#links');
                $linksTable.empty(); // Очищаем таблицу перед добавлением новых данных

                links.forEach(link => {
                    // Формируем строки таблицы с гиперссылками

                    const shortUrl = link.shortUrl.startsWith("http") ? link.shortUrl : `http://${link.shortUrl}`;
                    $linksTable.append(`
                    <tr>
                        <td>${link.name}</td>
                          <td><a href="${shortUrl}" target="_blank">${shortUrl}</a></td>
                        <td><a href="${link.originalUrl}" target="_blank">${link.originalUrl}</a></td>
                        <td>${link.expirationDate}</td>
                        <td>${link.limitOfClicks}</td>
                        <td>${link.numberOfClicks}</td>
                        <td><button class="btn btn-danger btn-sm delete-btn" data-url="${link.shortUrl}">Delete</button></td>
                    </tr>
                `);
                });

                // Показ таблицы, если она скрыта
                if (links.length > 0) {
                    $("#resultLinks").show();
                } else {
                    $("#resultLinks").hide(); // Скрываем, если нет ссылок
                }
            },
            error: function (xhr) {
                alert(`Error loading links: ${xhr.responseText}`);
            }
        });
    }


    // Обработка удаления ссылки
    $('#links').on('click', '.delete-btn', function () {
        const shortUrl = $(this).data('url');

        $.ajax({
            url: `${apiUrl}/delete`,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ shortUrl }),
            success: function () {
                loadLinks(); // Обновляем список после удаления
            },
            error: function (xhr) {
                alert(`Error deleting link: ${xhr.responseText}`);
            }
        });
    });



    // Инициализация загрузки ссылок
    loadLinks();
});
