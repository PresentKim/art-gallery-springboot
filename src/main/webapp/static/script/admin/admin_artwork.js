function deleteSelectedArtworks() {
    document.querySelectorAll('.check-box:checked').forEach($checkbox => {
        const $ul = $checkbox.parentElement.parentElement.parentElement;

        axios.delete('/api/artworks/' + $checkbox.value)
            .then(() => $ul.remove())
            .catch(error => {
                defaultAjaxHandler(error.response);
            });
    });
}