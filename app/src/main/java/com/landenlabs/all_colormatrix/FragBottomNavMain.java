package com.landenlabs.all_colormatrix;

/*
 * Copyright (C) 2019 Dennis Lang (landenlabs@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Fragment which setups a page with bottom and side navigation.
 */
public class FragBottomNavMain extends FragBottomNavBase  {

    public View onCreateView(@NonNull LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_bottom_nav_main, container, false);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.sideNavFragment);
        // NavGraph bottomNavGraph = navController.getNavInflater().inflate(R.navigation.nav_bottom);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.bottomNavFragment);
        BottomNavigationView bottomNavigation = view.findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

}
