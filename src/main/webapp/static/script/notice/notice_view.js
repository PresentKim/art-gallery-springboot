async function deleteNotice(nseq) {
    try {
        await axios.delete(`/api/notices/${nseq}`);
        alert("소식지가 삭제되었습니다");
        location.href = '/notice';
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}
