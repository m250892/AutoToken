package com.example.manoj.autotokencompletelib.searchtext;

//When the user clicks on a token...
    public enum TokenClickStyle {
        None(false), //...do nothing, but make sure the cursor is not in the token
        Delete(false),//...delete the token
        Select(true),//...select the token. A second click will delete it.
        SelectDeselect(true);

        private boolean mIsSelectable = false;

        TokenClickStyle(final boolean selectable) {
            mIsSelectable = selectable;
        }

        public boolean isSelectable() {
            return mIsSelectable;
        }
    }