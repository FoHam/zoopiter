deleteImageBtn.addEventListener('click',deleteImage_f);
// 등록된 이미지 삭제
function deleteImage_f(e){
  changeServerImg(null);
  pic.src='/img/img_profile1.jpg';
  var previewImage = document.querySelector(".preview");
    previewImage.src = "location.replace(`/mypage/${dataset.petNum}/edit`);"; // 미리보기 이미지 초기화
}

/* 사진업로드 */
function DropFile(dropAreaId, fileListId) {
  let dropArea = document.getElementById(dropAreaId);
  let fileList = document.getElementById(fileListId);

  function preventDefaults(e) {
    e.preventDefault();
    e.stopPropagation();
  }

  function highlight(e) {
    preventDefaults(e);
    dropArea.classList.add('highlight');
  }

  function unhighlight(e) {
    preventDefaults(e);
    dropArea.classList.remove('highlight');
  }

  function handleDrop(e) {
    unhighlight(e);
    let dt = e.dataTransfer;
    let files = dt.files;

    handleFiles(files);

    const fileList = document.getElementById(fileListId);
    if (fileList) {
      fileList.scrollTo({ top: fileList.scrollHeight });
    }
  }

  function handleFiles(files) {
    files = [...files];
    // files.forEach(uploadFile);
    // 이미지 파일 삭제 로직 추가
      document.getElementsByName("deleteImage")[0].value = "true";

      // 미리보기 이미지 출력 로직
      var previewImage = document.querySelector(".preview");
      var file = files[0];
      var reader = new FileReader();
      reader.onloadend = function () {
        previewImage.src = reader.result;
      }
      if (file) {
        reader.readAsDataURL(file);
      } else {
        previewImage.src = "";
      }
    files.forEach(previewFile);
  }

  function previewFile(file) {
    console.log(file);
    renderFile(file);
  }

  function renderFile(file) {
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = function () {
      let img = dropArea.getElementsByClassName('preview')[0];
      img.src = reader.result;
      img.style.display = 'block';
    };
  }

  dropArea.addEventListener('dragenter', highlight, false);
  dropArea.addEventListener('dragover', highlight, false);
  dropArea.addEventListener('dragleave', unhighlight, false);
  dropArea.addEventListener('drop', handleDrop, false);

  return {
    handleFiles,
  };
}

const dropFile = new DropFile('drop-file', 'files');

/* 등록완료 */
function modify() {
  Swal.fire({
    title: '등록하시겠습니까?',
    text: '',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#333',
    cancelButtonColor: '#ffd88f',
    confirmButtonText: '등록하기',
    cancelButtonText: '취소하기',
  }).then(result => {
    if (result.isConfirmed) {
      location.replace('./mypage_main.html');
    }
  });
}

function petModify({dataset}) {
  Swal.fire({
    title: '수정페이지로 이동하시겠습니까?',
    text: '등록한 반려동물정보를 수정하러 이동합니다',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#333',
    cancelButtonColor: '#ffd88f',
    confirmButtonText: '수정하기',
    cancelButtonText: '취소하기',
  }).then(result => {
    if (result.isConfirmed) {
        location.replace(`/mypage/${dataset.petNum}/edit`);
    }
  });
}
 /** 삭제 */
  const $btn_del = document.getElementById('btn_del');

  const btn_del = e => {
    if(confirm('삭제 하시겠습니까?')){
      globalThis.location.href = `/mypage/${petNum.value}/del`;
    }
  }
  $btn_del.addEventListener('click',btn_del,false);

// 반려동물 정보 추가(2개이상) 되면 이미지 슬라이드
$(document).ready(function () {
  const profileAreas = $('.profile-area');
  const profileList = $('.profile-list__areas');
  if (profileAreas.length > 2) {
    // profile-area 태그가 2개 이상일 때
    $('.profile-list__areas').slick({
      // slick 라이브러리를 적용할 부분 선택자
      slidesToShow: 2, // 한 화면에 보여줄 슬라이드 개수
      slidesToScroll: 1, // 슬라이드 이동 시 이동할 슬라이드 개수
      arrows: false,
    });
  } else {
    profileList.css('width', '40%');
    profileAreas.css('width', '70%');
  }
});
