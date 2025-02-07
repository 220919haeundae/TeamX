$(document).ready(function () {
    var container = $('#map')[0]; // jQuery 객체에서 DOM 요소 추출
    var options = {
        center: new kakao.maps.LatLng(37.5511694, 126.9882266),
        level: 8
    };

    var map = new kakao.maps.Map(container, options);
    var markers = []; // 마커를 저장할 배열

    // 모달 요소 및 닫기 버튼
    var modal = $("#markerModal");
    var closeButton = $(".close");

    // 모달 닫기
    closeButton.on("click", function () {
        modal.hide();
    });

    // 모달 외부 클릭 시 닫기
    $(window).on("click", function (event) {
        if (event.target === modal[0]) {
            modal.hide();
        }
    });

    // 기존 마커를 모두 삭제하는 함수
    function clearMarkers() {
        markers.forEach(marker => marker.setMap(null)); // 모든 마커 제거
        markers = []; // 배열 초기화
    }

    // 마커를 생성하는 함수
    function addMarker(position) {
        var marker = new kakao.maps.Marker({
            position: position,
            clickable: true
        });

        marker.setMap(map); // 마커 표시
        markers.push(marker); // 배열에 저장

        // 마커 클릭 시 모달 띄우기
        kakao.maps.event.addListener(marker, 'click', function () {
            $.ajax({
                url: 'exmap/exhibition/info',
                type: 'GET',
                data: {
                    lat: position.getLat(),
                    lot: position.getLng()
                },
                success: function (response) {
                    if (response) {
                        $('.item-title').text(response.title);
                        $('.item-place').text(response.place);
                        $('.item-date').text(response.exDate);
                        $('.item-image').attr('src', response.mainImg);
                        // input hidden에 exhibitionNo 값 저장
                        $('#detailExhibitionPage input[name="id"]').val(response.exhibitionNo);

                        let kakaoMapUrl = `https://map.kakao.com/link/to/${encodeURIComponent(response.title)},${response.lot},${response.lat}`;
                        $('.modal-content button').attr('onclick', `window.open('${kakaoMapUrl}', '_blank')`);
                        modal.show();
                    } else {
                        alert("해당 위치에 전시 정보가 없습니다.");
                    }
                },
                error: function (xhr, status, error) {
                    console.error("AJAX 요청 오류:", status, error);
                }
            });
        });
    }

    // 지역 좌표 가져오기 함수
    window.getCoordinates = function (element) {
        $("#dropdownMenuButton").text($(element).text()); // 드롭다운 선택 유지
        var selectedRegion = $("#dropdownMenuButton").text(); // 선택된 지역명 가져오기

        $.ajax({
            url: '/exmap/coordinates',
            type: 'GET',
            data: { guname: selectedRegion },
            success: function (result) {
                clearMarkers(); // 기존 마커 제거

                result.forEach(item => {
                    // 서울시 열린데이터가 lot을 위도로, lat을 경도로 잘못 기입함
                    var position = new kakao.maps.LatLng(item.lot, item.lat);
                    addMarker(position);
                });
            },
            error: function (xhr, status, error) {
                console.error("AJAX 요청 오류:", status, error);
            }
        });
    };

    // 전시 상세 페이지로 이동
    $('.item-textarea').on("click", function () {
        $('#detailExhibitionPage').submit();
    });
});
