function deleteSelectedArtworks() {
    function deleteAnimate($ul) {
        $ul.classList.add('deleted');
        setTimeout(() => $ul.remove(), 500);
    }

    document.querySelectorAll('.check-box:checked').forEach($checkbox => {
        const $ul = $checkbox.parentElement.parentElement.parentElement;
        $ul.classList.add('delete-target');

        axios.delete('/api/artworks/' + $checkbox.value)
            .then(() => deleteAnimate($ul))
            .catch(error => {
                $ul.classList.remove('delete-target');
                defaultAjaxHandler(error.response);
            });
    });
}