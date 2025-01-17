/*
 * Copyright 2021-Present Okta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.okta.idx.android.directauth.sdk.viewFactories

import android.view.View
import android.view.ViewGroup
import com.okta.idx.android.databinding.FormRegisterBinding
import com.okta.idx.android.databinding.RowRegisterAttributeBinding
import com.okta.idx.android.directauth.sdk.FormViewFactory
import com.okta.idx.android.directauth.sdk.forms.RegisterForm
import com.okta.idx.android.directauth.sdk.util.bindText
import com.okta.idx.android.directauth.sdk.util.inflateBinding

internal class RegisterFormViewFactory : FormViewFactory<RegisterForm> {
    override fun createUi(
        references: FormViewFactory.References,
        form: RegisterForm
    ): View {
        val binding = references.parent.inflateBinding(FormRegisterBinding::inflate)

        for (attribute in form.viewModel.attributes) {
            attribute.bind(binding.root, references)
        }

        binding.registerButton.setOnClickListener {
            form.register()
        }

        binding.signOutButton.setOnClickListener {
            form.signOut()
        }

        return binding.root
    }

    private fun RegisterForm.Attribute.bind(parent: ViewGroup, references: FormViewFactory.References) {
        val binding = parent.inflateBinding(RowRegisterAttributeBinding::inflate)

        binding.textInputLayout.hint = label

        bindText(
            editText = binding.editText,
            textInputLayout = binding.textInputLayout,
            valueField = ::value,
            errorsLiveData = errorsLiveData,
            references = references
        )

        parent.addView(binding.root, parent.childCount - 2)
    }
}
