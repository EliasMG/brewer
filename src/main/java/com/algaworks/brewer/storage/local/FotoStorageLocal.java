package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath("HOME", ".brewerfotos"));
		criarPastas();
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas criadas para salvar fotos.");
				LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
				LOGGER.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome= null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária", e);
			}
			
		}
		
		return novoNome;
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
		}
		
		return novoNome;
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nomeFoto) {
		
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto temporária", e);
		}
	}

	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo foto para destino final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thmbnail", e);
		}
	}

	@Override
	public byte[] recuperar(String nomeFoto) {
		try {
			return Files.readAllBytes(this.local.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto", e);
		}
	}

	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve("thumbnail." + foto));
		} catch (IOException e) {
			LOGGER.warn(String.format("Erro apagando foto '%s'. Mensagem: %s", foto, e.getMessage()));
		}
	}

}
