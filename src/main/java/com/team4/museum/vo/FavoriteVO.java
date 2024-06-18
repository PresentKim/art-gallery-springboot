package com.team4.museum.vo;

public class FavoriteVO extends ArtworkVO {

    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public static FavoriteVO fromArtwork(ArtworkVO artwork) {
        FavoriteVO favorite = new FavoriteVO();
        favorite.setAseq(artwork.getAseq());
        favorite.setName(artwork.getName());
        favorite.setCategory(artwork.getCategory());
        favorite.setArtist(artwork.getArtist());
        favorite.setYear(artwork.getYear());
        favorite.setMaterial(artwork.getMaterial());
        favorite.setSize(artwork.getSize());
        favorite.setDisplayyn(artwork.getDisplayyn());
        favorite.setContent(artwork.getContent());
        favorite.setImage(artwork.getImage());
        favorite.setSavefilename(artwork.getSavefilename());
        return favorite;
    }

}
