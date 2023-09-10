package com.vascodes.spaced.View;

public interface FlashcardView {
    void onFlashcardAddedSuccessfully();

    void onFlashcardAddedFailed();

    void onFlashcardAddedFailed(String failMessage);
}
