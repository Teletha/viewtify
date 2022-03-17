# Changelog

## [2.3.0](https://www.github.com/teletha/viewtify/compare/v2.2.1...v2.3.0) (2022-03-14)


### Features

* Add DockSystem#validate. ([ff2b1f8](https://www.github.com/teletha/viewtify/commit/ff2b1f86f32ab88713f60a880c5b8457f5a01bf4))
* Support more flexible dock layout. ([ed5abe6](https://www.github.com/teletha/viewtify/commit/ed5abe65e16f87ebaceac9a3143f15fd34c11d0d))


### Bug Fixes

* Activate automatic gc more. ([fa8f921](https://www.github.com/teletha/viewtify/commit/fa8f921662fc32f67ef1400a5b3babd2fc029738))
* Better draggable area. ([c4dacab](https://www.github.com/teletha/viewtify/commit/c4dacabf81126c4a42e9c0721f8272926e56f971))
* Canvas enable to override bounded size. ([8dc35aa](https://www.github.com/teletha/viewtify/commit/8dc35aa924a220a02ccb34f05e3868e1dee0f6e9))
* Dragging dock item is broken. ([886ccb9](https://www.github.com/teletha/viewtify/commit/886ccb9011f6d124b977e219717444f0a0c7b93c))
* Restore the location of separated window. ([c16dc7d](https://www.github.com/teletha/viewtify/commit/c16dc7d014bf493621e5841abf96daaecbd70ff8))

### [2.2.1](https://www.github.com/teletha/viewtify/compare/v2.2.0...v2.2.1) (2022-01-02)


### Bug Fixes

* Stabilize headless mode. ([fd5c781](https://www.github.com/teletha/viewtify/commit/fd5c781c2964d87412258b5bf782cb329087bccb))

## [2.2.0](https://www.github.com/teletha/viewtify/compare/v2.1.0...v2.2.0) (2022-01-02)


### Features

* UIWeb#load can load HTML contents directly. ([d467852](https://www.github.com/teletha/viewtify/commit/d4678529833fc675001bf18896add40ba13280ec))


### Bug Fixes

* headless mode ([b03cd70](https://www.github.com/teletha/viewtify/commit/b03cd705dfa31896c7e9fabf9990094a5ff8b110))
* Invoke Viewtify#browser on FX thread. ([ba96bbe](https://www.github.com/teletha/viewtify/commit/ba96bbedc22fef41dcb5a8c970720ca6f69516d3))

## [2.1.0](https://www.github.com/teletha/viewtify/compare/v2.0.0...v2.1.0) (2021-12-30)


### Features

* Add UIWeb#script to execute an arbitrary code. ([1177f72](https://www.github.com/teletha/viewtify/commit/1177f72c802d4c96340fa154d47c0bf60f29db05))
* Enable automatic GC. ([998cce3](https://www.github.com/teletha/viewtify/commit/998cce367ab34d532706501e8887959c05c7f034))
* Support headless mode to use Viewtify#inHeadless. ([51b40c7](https://www.github.com/teletha/viewtify/commit/51b40c770cad56e7d653b9f040e65eba6fbc7bec))
* update javafx. ([805350f](https://www.github.com/teletha/viewtify/commit/805350f6fe61662d3c08500c99adca95498b355b))

## 2.0.0 (2021-03-29)


### Bug Fixes

* Animete Toast in UI thread. ([3362ab7](https://www.github.com/Teletha/viewtify/commit/3362ab7bd2f40728fa822b711ffbeccdba85ced7))
* BorderBox hides dock window. ([ab0284d](https://www.github.com/Teletha/viewtify/commit/ab0284db2fda349efcc32088f8556bf1bc6d3bcf))
* Docking window accepts only dockable pane. ([df28ad0](https://www.github.com/Teletha/viewtify/commit/df28ad091432811fca70cca249238e05bfdb6e13))
* DropStage is not closed when drag and drop fails. ([6704724](https://www.github.com/Teletha/viewtify/commit/6704724bbd88e7864be840a694ade8e854aec56d))
* Enable CI. ([f72a6ff](https://www.github.com/Teletha/viewtify/commit/f72a6ff3d836e425e61fde2c91e7c42da56c51ce))
* Failing value transformation crush UI. ([fc43ed5](https://www.github.com/Teletha/viewtify/commit/fc43ed517fd52b90f5e11a852524718bdbced2d2))
* Initialize JavaFX capably. ([71cf0d7](https://www.github.com/Teletha/viewtify/commit/71cf0d7f4d41215987c02c6162133dd19feb3e90))
* Make code compilable by javac. ([f55a019](https://www.github.com/Teletha/viewtify/commit/f55a0197da9b08cc733d35b1ee70a581f928a8b0))
* Non ui-related test fails by toolkit not initialized. ([b19764f](https://www.github.com/Teletha/viewtify/commit/b19764f3f3325a78c5d5634fc3202ed901e7ecb2))
* NPE in CSS. ([93165b1](https://www.github.com/Teletha/viewtify/commit/93165b19811a17105fcba730b57b5d166c3d36f9))
* Remove window location info on closing the dock window. ([c1140e6](https://www.github.com/Teletha/viewtify/commit/c1140e6854c67bcb21c1769ecd9aa16ea5f2d1e4))
* Restart by exewrap is broken. ([44a4753](https://www.github.com/Teletha/viewtify/commit/44a4753074c454053a6baf78fc00d33c8668028c))
* Runtime lambda factory can't detect valid intersection type. ([3ce4d2c](https://www.github.com/Teletha/viewtify/commit/3ce4d2ca12df5506eed531cf20ee2a1e907d4283))
* SplitPane can't keep the dividier's position when content is ([10c4cc5](https://www.github.com/Teletha/viewtify/commit/10c4cc5f524d534c9c6f4ed98f26020dfe6804cc))
* StyleHelper#unstyle is wrong. ([edc413c](https://www.github.com/Teletha/viewtify/commit/edc413c5084c0b82235074cc98e398f4a1cf5ab9))
* Tab implementation don't bind id. ([d053264](https://www.github.com/Teletha/viewtify/commit/d0532644342f6ea7e464856e6d9aed1e2992a6a4))
* Throws NPE when dock system creates new window with icon. ([672ca93](https://www.github.com/Teletha/viewtify/commit/672ca935f1ab47d42b9f3392789e565b0f613825))
* Traversal action can skip disabled item. ([d1e058d](https://www.github.com/Teletha/viewtify/commit/d1e058dc2c159be681e4624752882624eadb3042))
* TypeMappingProvider on table column recognize subclass of the ([75ccf05](https://www.github.com/Teletha/viewtify/commit/75ccf05d1dacdd46b5cc3d85511eaf76cfacef9d))
* UIDatePicker's action throws NPE when it has null value. ([0e7475d](https://www.github.com/Teletha/viewtify/commit/0e7475d9790b2fbce95d9a5b40f136be095afeac))
* UIListView filter is broken. ([de22ab7](https://www.github.com/Teletha/viewtify/commit/de22ab7707b5806018a3cab26a525da53285ffb3))
* UITableCell brokes rendering. ([401ec8a](https://www.github.com/Teletha/viewtify/commit/401ec8ad760d40852b7a3a73f951b8e3869cee0e))
* UITabPane selects model by mouse scroll. ([8046e0a](https://www.github.com/Teletha/viewtify/commit/8046e0a800321b80b556379c05fc2d29efc96281))
* UIText can ignore blank value. ([1f1ee51](https://www.github.com/Teletha/viewtify/commit/1f1ee518d7f17a0c7bbffd8a8e31061518c87967))
* Untrack main window. ([e572d47](https://www.github.com/Teletha/viewtify/commit/e572d47aa6becb69983ac2431ac38bee590fbae6))
* User click event is broken. ([c706cf1](https://www.github.com/Teletha/viewtify/commit/c706cf178f51bf355b053d4a6c7407c51698d731))
* UserInterface#restore ignores invalid stored value. ([09c397d](https://www.github.com/Teletha/viewtify/commit/09c397ddbf70022b7788235bc078ad75c9609fd9))
* Validation doesn't verify initial state. ([cbb0eb0](https://www.github.com/Teletha/viewtify/commit/cbb0eb07c72c30a44a860cecfb2f448a9c2dd743))
