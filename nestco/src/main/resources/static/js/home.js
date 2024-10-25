$(document).ready(function () {
        function previewImage(event) {
                const reader = new FileReader();
                reader.onload = function () {
                    const $output = $('#imagePreview');
                    $output.attr('src', reader.result);
                    $output.show();
                };
                reader.readAsDataURL(event.target.files[0]);
            }

            // 파일 입력 필드가 변경되면 미리보기 기능 실행
            $('#fileInput').on('change', function (event) {
                previewImage(event);
            });

        $('#filter-form').submit(function (e) {
            e.preventDefault();

            const sortOrder = $('#sortOrder').val();
            const status = $('#status').val();
            const location = $('#location').val();

            const params = $.param({
                sortOrder: sortOrder,
                status: status,
                location: location
            });

            $.get(`/items/filter?${params}`, function (data) {
                // 필터링된 결과를 item-container에 업데이트
                const $itemContainer = $('.item-container');
                $itemContainer.empty();

                $.each(data.items, function (index, item) {
                    const itemCard = `
                        <div class="item-card">
                            <a href="/items/${item.id}" class="detail-link">
                                <img src="${item.imagePath}" alt="상품 이미지" class="item-image">
                                <div class="item-details">
                                    <h3>${item.title}</h3>
                                    <p>${item.content}</p>
                                </div>
                            </a>
                        </div>
                    `;
                    $itemContainer.append(itemCard);
                });
            });
        });
    });