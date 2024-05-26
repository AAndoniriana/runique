plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "mg.jaava.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}