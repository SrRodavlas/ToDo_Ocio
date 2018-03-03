package com.grupo5.todo_ocio.Camara;


import android.app.Activity;
import android.app.DialogFragment;

import com.grupo5.todo_ocio.PhotoDialogFragment;

public interface fragment_interface {

    public void onDialogPositiveClick(DialogFragment dialog);
    public void onDialogNegativeClick(DialogFragment dialog);
}

//    fragment_interface mListener;
//
//    // Use this instance of the interface to deliver action events
//    PhotoDialogFragment.PhotoDialogListener mListener;
//
//    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (PhotoDialogFragment.PhotoDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement PhotoDialogListener");
//        }
//    }