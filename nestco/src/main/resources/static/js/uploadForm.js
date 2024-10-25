$(document).ready(function() {
    // 대분류 선택 시 중분류 가져오기
    $('#parentCategory').on('change', function() {
        let parentId = $(this).val();
        if (parentId) {
            $.ajax({
                url: '/items/middle/' + parentId,
                method: 'GET',
                success: function(response) {
                    $('#middleCategory').empty().append('<option value="">중분류 선택</option>');
                    $('#childCategory').empty().append('<option value="">소분류 선택</option>');
                    response.forEach(function(category) {
                        $('#middleCategory').append('<option value="' + category.id + '">' + category.name + '</option>');
                    });
                    $('#middleCategory').prop('disabled', false);
                }
            });
        } else {
            $('#middleCategory').empty().append('<option value="">중분류 선택</option>').prop('disabled', true);
            $('#childCategory').empty().append('<option value="">소분류 선택</option>').prop('disabled', true);
        }
    });

    // 중분류 선택 시 소분류 가져오기
    $('#middleCategory').on('change', function() {
        let middleId = $(this).val();
        if (middleId) {
            $.ajax({
                url: '/items/child/' + middleId,
                method: 'GET',
                success: function(response) {
                    $('#childCategory').empty().append('<option value="">소분류 선택</option>');
                    response.forEach(function(category) {
                        $('#childCategory').append('<option value="' + category.id + '">' + category.name + '</option>');
                    });
                    $('#childCategory').prop('disabled', false);
                }
            });
        } else {
            $('#childCategory').empty().append('<option value="">소분류 선택</option>').prop('disabled', true);
        }
    });
      $('#file').on('change', function(event) {
            const fileInput = event.target;
            const files = fileInput.files;
            const $previewImage = $('#imagePreview');  // 미리보기 이미지를 jQuery로 선택

            if (files && files[0]) {
                const reader = new FileReader();

                // 이미지 로드가 완료되면 미리보기 태그에 이미지를 표시
                reader.onload = function(e) {
                    $previewImage.attr('src', e.target.result);
                    $previewImage.show();  // 이미지를 표시
                };

                // 첫 번째 파일 읽기
                reader.readAsDataURL(files[0]);
            } else {
                // 파일이 없으면 미리보기 이미지를 숨김
                $previewImage.hide();
            }
        });
});