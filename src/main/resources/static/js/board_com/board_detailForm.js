// ���� ȭ������ ����
const $modifyBtn = document.getElementById('modifyBtn');

$modifyBtn?.addEventListener('click', e => {
   const url = `/bbsc/${bbscId.value}/edit`;
   location.href = url;
});

// ����
const $delBtn = document.getElementById('delBtn');
$delBtn?.addEventListener('click', e => {
  if(confirm('�����Ͻðڽ��ϱ�?')){
    const url = `/bbsc/${bbscId.value}/del`;
    location.href = url;
  }
});

