const images = [
    'images2/guitar_main.jpg',
    'images2/guitar_main2.jpg',
    'images2/guitar_main3.jpg',
    'images2/guitar_main4.jpg',
    'images2/guitar_main5.jpg'
    // 추가 이미지 경로
];

let currentIndex = 0;

function changeImage() {
    const imgElement = document.getElementById('slider');
    imgElement.classList.add('blur'); // 블러 효과 추가

    // 블러 효과가 적용된 후 이미지를 변경
    setTimeout(() => {
        imgElement.src = images[currentIndex];
        imgElement.classList.remove('blur'); // 블러 효과 제거
    }, 1000); // 블러 효과 지속 시간과 맞추기 위해 1초 후에 실행

    currentIndex = (currentIndex + 1) % images.length;
}

// 처음 이미지 설정
changeImage();

// 5초 간격으로 이미지 변경
setInterval(changeImage, 10000);