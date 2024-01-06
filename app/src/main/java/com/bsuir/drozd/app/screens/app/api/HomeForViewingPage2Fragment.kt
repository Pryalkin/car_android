package com.bsuir.drozd.app.screens.app.api

import android.Manifest
import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.drozd.Singletons.navigator
import com.bsuir.drozd.app.dto.ApplicationDTO
import com.bsuir.drozd.app.dto.CarDTO
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.enum.Category
import com.bsuir.drozd.app.enum.MachineDrive
import com.bsuir.drozd.app.views.ApiViewModel
import com.bsuir.drozd.databinding.FragmentHomeForViewingPage2Binding


import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.lang3.math.NumberUtils
import java.io.File
import kotlin.properties.Delegates

class HomeForViewingPage2Fragment : Fragment(){

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentHomeForViewingPage2Binding
    private val viewModel by viewModels<ApiViewModel>()
    private lateinit var carAdapter: CarAdapter
    private lateinit var appAdapter: AppAdapter
    private lateinit var myLauncher : ActivityResultLauncher<Intent>

    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val uri = data?.data
                uri?.let { uploadFile(it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
        validatePermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeForViewingPage2Binding.inflate(inflater)
        when (pageNumber) {
            0 -> {
                customizeScreen(b = true, b1 = false, b2 = false)
                var categories: List<String> = Category.values().map { it.category }
                seeSpinner(categories, binding.spinnerCategory)
                binding.find.setOnClickListener {
                    configureTheAdapterForCar()
                    val category = binding.spinnerCategory.selectedItem.toString()
                    when(category){
                        Category.COMFORT_CLASS.category -> {
                            Toast.makeText(requireContext(), Category.COMFORT_CLASS.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.COMFORT_CLASS.category)
                        }
                        Category.BUSINESS_CLASS.category -> {
                            Toast.makeText(requireContext(), Category.BUSINESS_CLASS.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.BUSINESS_CLASS.category)
                        }
                        Category.PREMIUM.category -> {
                            Toast.makeText(requireContext(), Category.PREMIUM.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.PREMIUM.category)
                        }
                        Category.SUV.category-> {
                            Toast.makeText(requireContext(), Category.SUV.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.SUV.category)
                        }
                        Category.CONVERTIBLES.category -> {
                            Toast.makeText(requireContext(), Category.CONVERTIBLES.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.CONVERTIBLES.category)
                        }
                        Category.SPORT.category -> {
                            Toast.makeText(requireContext(), Category.SPORT.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.SPORT.category)
                        }
                        Category.MINIBUSES.category -> {
                            Toast.makeText(requireContext(), Category.MINIBUSES.category, Toast.LENGTH_LONG).show()
                            viewModel.getCategory(Category.MINIBUSES.category)
                        }
                    }
                }
            }
            1 -> {
                customizeScreen(b = false, b1 = true, b2 = false)
                configureTheAdapterForApp()
                viewModel.getApp()
            }
            2 -> {
                customizeScreen(b = false, b1 = false, b2 = true)
                var categories: List<String> = Category.values().map { it.category }
                var wheels: List<String> = MachineDrive.values().map { it.wheel }
                seeSpinner(categories, binding.spinCat)
                seeSpinner(wheels, binding.spMachineDrive)
                binding.imageView.setOnClickListener {
                    openImageChooser()
                }
                binding.btnSend.setOnClickListener {
                    binding.apply {
                        if (edBrand.text.toString() == "") sendMessage("Введите марку автомобиля!")
                        else if (edEngineCapacity.text.toString() == "") sendMessage("Введите мощность двигателя!")
                        else if (!NumberUtils.isParsable(edEngineCapacity.text.toString())) sendMessage("Вы ввели не число в поле мощность двигателя!")
                        else if (edVolumeCapacity.text.toString() == "") sendMessage("Введите объем двигателя!")
                        else if (!NumberUtils.isParsable(edVolumeCapacity.text.toString())) sendMessage("Вы ввели не число в поле объем двигателя!")
                        else if (edColor.text.toString() == "") sendMessage("Введите цвет автомобиля!")
                        else if (edConsumption.text.toString() == "") sendMessage("Введите расход автомобиля!")
                        else if (!NumberUtils.isParsable(edConsumption.text.toString())) sendMessage("Вы ввели не число в поле расход автомобиля!")
                        else if (edNumberOfSeats.text.toString() == "") sendMessage("Введите количество мест!")
                        else if (!NumberUtils.isParsable(edNumberOfSeats.text.toString())) sendMessage("Вы ввели не число в поле количкество мест!")
                        else if (edPrice.text.toString() == "") sendMessage("Введите цену аренды автомобиля!")
                        else if (!NumberUtils.isParsable(edPrice.text.toString())) sendMessage("Вы ввели не число в поле аренда автомобиля!")
                        else {
                            val category = binding.spinCat.selectedItem.toString()
                            val machineDrive = binding.spMachineDrive.selectedItem.toString()
                            val brand = edBrand.text.toString()
                            val engineCapacity = edEngineCapacity.text.toString().toInt()
                            val volumeCapacity = edVolumeCapacity.text.toString().toDouble()
                            val color = edColor.text.toString()
                            val consumption = edConsumption.text.toString().toDouble()
                            val numberOfSeats = edNumberOfSeats.text.toString().toInt()
                            val price = edPrice.text.toString().toDouble()
                            val car = CarDTO(
                                category = category,
                                brand = brand,
                                engineCapacity = engineCapacity,
                                volumeCapacity = volumeCapacity,
                                machineDrive = machineDrive,
                                color = color,
                                consumption = consumption,
                                numberOfSeats = numberOfSeats,
                                price = price
                            )
                            val image: MultipartBody.Part = uploadFile(selectedImageUri)
                            viewModel.createCar(car, image)
                            edBrand.setText("")
                            edEngineCapacity.setText("")
                            edVolumeCapacity.setText("")
                            edColor.setText("")
                            edConsumption.setText("")
                            edNumberOfSeats.setText("")
                            edPrice.setText("")
                        }
                    }
                }
            }
        }
        return binding.root
    }

    private fun configureTheAdapterForApp() {
        appAdapter = AppAdapter()
        viewModel.apps.observe(viewLifecycleOwner){
            appAdapter.apps = it
        }
        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewApp.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewApp.adapter = appAdapter
        val itemAnimatorAnnouncement = binding.recyclerViewApp.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png, image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imageView.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun seeSpinner(str: List<String>, spinnerForString: Spinner) {
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            str
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerForString.adapter = adapter
            spinnerForString.setSelection(0)
        }
    }

    private fun sendMessage(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
    }

    private fun customizeScreen(b: Boolean, b1: Boolean, b2: Boolean) {
        binding.apply {
            if (b) seeRent.visibility = View.VISIBLE
            else seeRent.visibility = View.GONE
            if (b1) seeApplication.visibility = View.VISIBLE
            else seeApplication.visibility = View.GONE
            if (b2) seeAdmin.visibility = View.VISIBLE
            else seeAdmin.visibility = View.GONE
        }
    }

    private fun configureTheAdapterForCar() {
        carAdapter = CarAdapter(object : CarActionListener{
            override fun onCarDetails(car: CarAnswerDTO) {
                navigator().showDetailCar(car)
            }
            override fun apply(car: CarAnswerDTO) {
                val dialog = AppDialog(car.id)
                dialog.show(requireActivity().supportFragmentManager, null)
            }
            override fun send(car: CarAnswerDTO) {
                val applicationDTO = ApplicationDTO(
                    car.id,
                    car.category,
                    Application.name,
                    Application.surname,
                    Application.patronymic,
                    Application.passport,
                    Application.mobile,
                    Application.amountOfDays
                )
                viewModel.application(applicationDTO);
                Application.name = ""
                Application.surname = ""
                Application.patronymic = ""
                Application.passport = ""
                Application.mobile = 0
                Application.amountOfDays = 0
            }
        })

        viewModel.cars.observe(viewLifecycleOwner){
            carAdapter.cars = it
        }
        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewCar.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewCar.adapter = carAdapter
        val itemAnimatorAnnouncement = binding.recyclerViewCar.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }
    }

    private fun uploadFile(uri: Uri?): MultipartBody.Part  {
        val file = File(getRealPathFromURI(uri))
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    private fun getRealPathFromURI(uri: Uri?): String? {
        var realPath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = uri?.let { context?.contentResolver?.query(it, projection, null, null, null) }
        if (cursor != null && cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            realPath = cursor.getString(column_index)
            cursor.close()
        }
        return realPath
    }

    private fun validatePermission() {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_LONG).show()
                }
                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Storage permission required")
                        .setMessage("In order to take a picture, you must grant this permission")
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                                dialogInterface, i ->
                            dialogInterface.dismiss()
                            token?.cancelPermissionRequest()
                        })
                        .setPositiveButton(R.string.ok, DialogInterface.OnClickListener {
                                dialogInterface, i ->
                            dialogInterface.dismiss()
                            token?.continuePermissionRequest()
                        })
                        .show()
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(requireActivity(), "Storage permission is required in order to take a photo with the camera", Toast.LENGTH_LONG).show()
                }
            }
            ).check()
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
        @JvmStatic
        fun newInstance(page: Int): HomeForViewingPage2Fragment {
            val fragment: HomeForViewingPage2Fragment = HomeForViewingPage2Fragment()
            val args = Bundle()
            args.putInt("num", page)
            fragment.arguments = args
            return fragment
        }
    }

}

