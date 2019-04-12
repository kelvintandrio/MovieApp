package algo.project.kelvin.catalogmovie.services;

interface LoadDataCallback {
    void onPreLoad();
    void onProgressUpdate(long progress);
    void onLoadSuccess();
    void onLoadFailed();
}
