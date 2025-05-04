package com.example.moleculeapi.controller;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.StandardGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api")
public class MoleculeController {

    @GetMapping(value = "/render", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] renderMolecule(@RequestParam String smiles) throws Exception {
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer molecule = sp.parseSmiles(smiles);

        int width = 300, height = 300;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        AtomContainerRenderer renderer = new AtomContainerRenderer(
                java.util.List.of(new StandardGenerator(), new BasicSceneGenerator()),
                new AWTFontManager()
        );

        renderer.setup(molecule, new Rectangle(width, height));
        renderer.paint(molecule, new AWTDrawVisitor(g2), new Rectangle(width, height), true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
