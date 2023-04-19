import { ajax } from '/js/ajax.js';

// 목록
const $listBtn = document.getElementById('listBtn');
$listBtn.addEventListener('click', e => {
    const url = '/bbsc/list';
    location.href = url;
});

// 수정 등록
const $addBtn = document.getElementById('addBtn');
$addBtn.addEventListener('click', e => {
    editForm.action = `${editForm.action}`;
    editForm.submit();
});

// 취소
const $cancleBtn = document.getElementById('cancleBtn');
$cancleBtn.addEventListener('click', e => {
    const url = `/bbsc/${bbscId.value}/detail`;
    location.href = url;
});

// 첨부파일 삭제
const $files = document.querySelector('.file');
$files?.addEventListener('click', e => {
    if(e.target.tagName != 'I') return;
    if(!confirm('삭제하시겠습니까?')) return;

    const $icon = e.target;
    const url = `/attach/${$icon.dataset.rid}`;
    ajax
        .delete(url)
        .then(res => res.json())
        .then(res => {
            if(res.header.rtcd == '00'){
                //첨부파일 정보 화면에서 제거
                removeAttachFileFromView(e);
            }else{
                console.log(res.rtmsg);
            }
        })
        .catch(console.error); //err=>console.error(err)
});
function removeAttachFileFromView(e){
    const $parent = document.getElementById('files');
    const $child = e.target.closest('.view__img');
    $parent.removeChild($child);
}