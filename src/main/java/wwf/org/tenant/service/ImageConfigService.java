package wwf.org.tenant.service;

import wwf.org.tenant.entity.ImageConfig;


public interface ImageConfigService {

    public ImageConfig getImage(Long id);
    public ImageConfig createImage(ImageConfig imageConfig);
    public ImageConfig updateImage(ImageConfig imageConfig);
}
