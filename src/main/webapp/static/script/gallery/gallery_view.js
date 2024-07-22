async function deleteGallery(gseq) {
    try {
        await axios.delete(`/api/galleries/${gseq}`);
        alert("갤러리가 삭제되었습니다");
        location.href = '/gallery';
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}