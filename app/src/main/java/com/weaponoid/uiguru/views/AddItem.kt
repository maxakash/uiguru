package com.weaponoid.uiguru.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.viewmodel.AddItemViewModel
import kotlinx.android.synthetic.main.add_item_fragment.*


class AddItem : Fragment() {


    private lateinit var viewModel: AddItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddItemViewModel::class.java)


        uiAddItem.setOnClickListener {
            viewModel.saveItem(
                author = uiAuthor.text.toString(),
                title = uiTitle.text.toString(),
                imageUrl = uiImageUrl.text.toString(),
                downloadUrl = uiDownloadUrl.text.toString(),
                source = uiSource.text.toString(),
                tag = uiTags.text.toString(),
                uiImages = uiImageList.text.toString(),
                context = context!!,
                type = uiType.selectedItem.toString(),
                fileName = uiFileName.text.toString(),
                category = uiCategory.selectedItem.toString()

            )
        }


    }

}
